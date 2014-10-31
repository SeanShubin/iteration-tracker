define([
    'jquery',
    'underscore',
    'underscore.string',
    'text!tasks/task-row-template.html',
    'text!tasks/tasks-table-template.html',
], function ($, _, _s, taskRowTemplate, tasksTableTemplate) {
    'use strict';
    function createTasksComponent(options) {
        var object, dom, taskNamesResponse, appendTaskToTable, taskRowsContainer, createTaskRow, initialize, newTaskEl;
        dom = $(tasksTableTemplate);
        taskRowsContainer = dom.find('.task-rows-container');
        newTaskEl = dom.find('.new-task');
        taskNamesResponse = function (response) {
            _.each(response.body, appendTaskToTable);
            return object;
        };
        appendTaskToTable = function (task) {
            var taskRow = createTaskRow(task);
            taskRowsContainer.append(taskRow)
        };
        createTaskRow = function (task) {
            var taskRowEl = $(taskRowTemplate);
            taskRowEl.find('.task-name').text(task);
            return taskRowEl;
        };
        initialize = function () {
            options.jsonOverHttp({method:'get', uri:'database/tasks'}).then(taskNamesResponse);
            newTaskEl.focus();
            return object;
        };
        object = {
            dom: dom,
            initialize: initialize
        };
        return object;
    }

    return createTasksComponent;
});
