(function() {
    'use strict';
    angular
        .module('demoApp')
        .factory('ActionLang', ActionLang);

    ActionLang.$inject = ['$resource'];

    function ActionLang ($resource) {
        var resourceUrl =  'api/action-langs/:id';

        return $resource(resourceUrl, {}, {
            'language': {
                method:'GET',
                url: '/api/action-langs/language-code',
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
