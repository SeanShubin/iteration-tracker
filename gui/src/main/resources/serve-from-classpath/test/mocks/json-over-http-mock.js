define(['jquery'], function ($) {
    'use strict';
    return function(){
        var requests, responses, jsonOverHttp;
        requests = [];
        responses = [];

        jsonOverHttp = function (options) {
            var response = $.Deferred();
            requests.push(options);
            responses.push(response);
            return response;
        };

        return {
            jsonOverHttp: jsonOverHttp,
            requests: requests,
            responses: responses
        };
    };
});
