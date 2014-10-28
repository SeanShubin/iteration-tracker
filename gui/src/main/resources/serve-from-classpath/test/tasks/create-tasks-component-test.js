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
});
