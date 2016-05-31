(function() {
    'use strict';
    angular
        .module('demoApp')
        .factory('Category', Category);

    Category.$inject = ['$resource'];

    function Category ($resource) {
        var resourceUrl =  'api/categories/:id';

        return $resource(resourceUrl, {}, {
            'enabled': {
                  method:'GET',
                  url: '/api/categories/enabled',
                  isArray: true
            },
            'query': { method: 'GET', isArray: true},
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
