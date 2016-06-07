(function() {
    'use strict';
    angular
        .module('demoApp')
        .factory('SubcategoryLang', SubcategoryLang);

    SubcategoryLang.$inject = ['$resource'];

    function SubcategoryLang ($resource) {
        var resourceUrl =  'api/subcategory-langs/:id';

        return $resource(resourceUrl, {}, {
            'language': {
                method:'GET',
                url: '/api/subcategory-langs/language-code',
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
