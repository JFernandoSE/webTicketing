(function() {
    'use strict';
    angular
        .module('demoApp')
        .factory('CategoryLang', CategoryLang);

    CategoryLang.$inject = ['$resource'];

    function CategoryLang ($resource) {
        var resourceUrl =  'api/category-langs/:id';

        return $resource(resourceUrl, {}, {
            'language': {
                method:'GET',
                url: '/api/category-langs/language-code',
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
