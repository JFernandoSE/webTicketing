(function() {
    'use strict';
    angular
        .module('demoApp')
        .factory('Request', Request);

    Request.$inject = ['$resource', 'DateUtils'];

    function Request ($resource, DateUtils) {
        var resourceUrl =  'api/requests/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateRequest = DateUtils.convertLocalDateFromServer(data.dateRequest);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateRequest = DateUtils.convertLocalDateToServer(data.dateRequest);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateRequest = DateUtils.convertLocalDateToServer(data.dateRequest);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
