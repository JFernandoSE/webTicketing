(function() {
    'use strict';
    angular
        .module('demoApp')
        .factory('Action', Action);

    Action.$inject = ['$resource'];

    function Action ($resource) {
        var resourceUrl =  'api/actions/:id';

        return $resource(resourceUrl, {}, {
            'enabled': {
                  method:'GET',
                  url: '/api/actions/enabled',
                  isArray: true
            },
            'query': {
                  method: 'GET',
                  isArray: true
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
