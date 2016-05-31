(function() {
    'use strict';
    angular
        .module('demoApp')
        .factory('Subcategory', Subcategory);

    Subcategory.$inject = ['$resource'];

    function Subcategory ($resource) {
        var resourceUrl =  'api/subcategories/:id';

        return $resource(resourceUrl, {}, {
            'enabled': {
                  method:'GET',
                  url: '/api/subcategories/enabled',
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
