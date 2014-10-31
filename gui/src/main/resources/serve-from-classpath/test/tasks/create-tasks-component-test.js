define([
    'lib/domReady!',
    'jquery',
    'qunit',
    'tasks/create-tasks-component'], function (dom, $, qunit, createTasksComponent) {
    'use strict';
    var createHelper = function () {
        var component, stubJsonOverHttp, requests, responses;
        requests = [];
        responses = [];
        stubJsonOverHttp = function (options) {
            var response = $.Deferred();
            requests.push(options);
            responses.push(response);
            return response;
        };
        component = createTasksComponent({jsonOverHttp: stubJsonOverHttp});
        return {
            component: component,
            requests: requests,
            responses: responses
        };
    };
    qunit.module('create-tasks-component-test');

    qunit.test('has input with placeholder for new task name', function () {
        var helper = createHelper();
        qunit.equal(helper.component.dom.find('.new-task').attr('placeholder'), 'New Task Name', 'has proper placeholder')
    });

    qunit.test('new task starts with focus', function () {
        var helper = createHelper();
        $('body').append(helper.component.dom);
        helper.component.initialize();
        qunit.equal(helper.component.dom.find('.new-task').is(':focus'), true, 'New task has focus upon render');
        helper.component.dom.remove();
    });

    qunit.test('initialize fetches current state', function () {
        var helper = createHelper();
        qunit.equal(helper.requests.length, 0, 'no requests before initialize');
        helper.component.initialize();
        qunit.equal(helper.requests.length, 1, 'one request after initialize');
        qunit.equal(helper.requests[0].uri, 'database/tasks', 'uri');
        qunit.equal(helper.requests[0].method, 'get', 'method');
        qunit.equal(helper.component.dom.find('.existing-task').length, 0, 'no tasks loaded before results');
        helper.responses[0].resolve({
            status: 200,
            body: ['Task A', 'Task B', 'Task C']
        });
        qunit.equal(helper.component.dom.find('.task-name').length, 3, '3 tasks');
        qunit.equal($(helper.component.dom.find('.task-name')[0]).text(), 'Task A', 'Task A');
        qunit.equal($(helper.component.dom.find('.task-name')[1]).text(), 'Task B', 'Task B');
        qunit.equal($(helper.component.dom.find('.task-name')[2]).text(), 'Task C', 'Task C');
    });
});
