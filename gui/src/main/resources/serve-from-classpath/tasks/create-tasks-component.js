define([
    'jquery',
    'underscore',
    'underscore.string',
    'text!todo/sean/components/task-row-template.html',
    'text!todo/sean/components/tasks-table-template.html',
], function ($, _, _s, taskRowTemplate, tasksTableTemplate) {
    'use strict';
    function createTasksComponent(options) {
        var dom, setTasks, appendTaskToTable, taskRowsContainer, createTaskRow;
        dom = $(tasksTableTemplate);
        taskRowsContainer = dom.find('.task-rows-container');
        setTasks = function (tasks) {
            _.each(tasks, appendTaskToTable)
        };
        setTasks(options.tasks);
        appendTaskToTable = function (task) {
            var taskRow = createTaskRow(task);
            taskRowsContainer.append(taskRow)
        };
        createTaskRow = function(task){
            var taskRowEl = $(taskRowTemplate);
            taskRowEl.find('.task-name').text(task)
        };
        return {
            dom: dom,
            setTasks: setTasks
        };
    }

    return createTasksComponent;
});
