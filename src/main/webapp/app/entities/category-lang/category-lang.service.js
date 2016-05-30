(function() {
    'use strict';
    angular
        .module('demoApp')
        .factory('CategoryLang', CategoryLang);

    CategoryLang.$inject = ['$resource'];

    function CategoryLang ($resource) {
        var resourceUrl =  'api/category-langs/:id';

        return $resource(resourceUrl, {}, {
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
