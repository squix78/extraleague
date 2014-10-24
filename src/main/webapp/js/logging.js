'use strict';
var loggingModule = angular.module('squix.services.logging', []);

loggingModule.factory(
    "traceService",
    function(){
        return({
            print: printStackTrace
        });
    }
);