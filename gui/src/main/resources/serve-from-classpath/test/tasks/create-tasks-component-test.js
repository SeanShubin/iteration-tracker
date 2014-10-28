define([
    'lib/domReady!',
    'jquery',
    'qunit',
    'tasks/create-tasks-component'], function (dom, $, qunit, createTasksComponent) {
    'use strict';
    qunit.module('create-tasks-component-test');
    qunit.test('has input with placeholder for new task name', function () {
        var component = createTasksComponent();
        qunit.equal(component.dom.find('.new-task').attr('placeholder'), 'New Task Name', 'has proper placeholder')
    });
    qunit.test('new task starts with focus', function () {
        var component = createTasksComponent();
        $('body').append(component.dom);
        component.initialize();
        qunit.equal(component.dom.find('.new-task').is(':focus'), true, 'New task has focus upon render');
        component.dom.remove();
    });
    qunit.test('initialize fetches current state', function () {
        var component, stubJsonOverHttp, deferredResponse;
        deferredResponse = $.Deferred();
        stubJsonOverHttp = function(options) {
            qunit.equal(options.uri, 'database/tasks', 'uri');
            qunit.equal(options.method, 'get', 'method');
            return deferredResponse;
        };
        component = createTasksComponent({jsonOverHttp: stubJsonOverHttp});
        component.initialize();
        qunit.equal(component.dom.find('.existing-task').length, 0, 'no tasks loaded before results');
        deferredResponse.resolve({
            status: 200,
            body: ['Task A', 'Task B', 'Task C']
        });
        qunit.equal(component.dom.find('.existing-task').length, 3, '3 tasks');
        qunit.equal(component.dom.find('.existing-task')[0], 'Task A', 'Task A');
        qunit.equal(component.dom.find('.existing-task')[1], 'Task B', 'Task B');
        qunit.equal(component.dom.find('.existing-task')[2], 'Task C', 'Task C');
    });
});
