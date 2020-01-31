package com.daffodil.renters.core.service.pooledactions.filteredquery;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.RecursiveTask;

abstract class GenericFilteredQuery<Postable> extends RecursiveTask<List<Postable>> {

    /**
     * Used by calling task to store index of parent in list
     */
    public int parentTag;

    /**
     * Run filtered query using filter params relevant to parent before going deeper.
     *
     * @return List of Postables to whom filtered children have to be attached
     */
    abstract Callable<List<Postable>> executeSelfFilteredQuery();

    /**
     * Generates the child query that has to be run.
     *
     * @param postable The parent whose Id will be used to set as a param in the filter.
     * @return Returns a GenericFilteredQuery of a child.
     */
    abstract Callable<GenericFilteredQuery> childQueryGenerator(Postable postable);

    /**
     * Attach a list of filtered children to parent
     *
     * @param parent    The parent that has to be attached to.
     * @param childList The list of children to be attached.
     */
    abstract Runnable attachChildListToParent(Postable parent, Object childList);

    @Override
    protected List<Postable> compute() {

        try {
            List<Postable> postables = executeSelfFilteredQuery().call();
            List<GenericFilteredQuery> childQueries = createChildTaskList(postables);

            childQueries.forEach(q -> q.fork());
            childQueries.forEach(q -> attachChildListToParent(postables.get(q.parentTag), q.join()).run());

            return postables;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new LinkedList<>();
    }

    private List<GenericFilteredQuery> createChildTaskList(List<Postable> postables) throws Exception {

        List<GenericFilteredQuery> childTasks = new LinkedList<>();

        if (postables != null) {
            for (int i = 0; i < postables.size(); i++) {
                Postable postable = postables.get(i);
                if (postable != null) {
                    GenericFilteredQuery childQuery = childQueryGenerator(postable).call();
                    if (childQuery != null) {
                        childQuery.parentTag = i;
                        childTasks.add(childQuery);
                    }
                }
            }
        }

        return childTasks;
    }

}
