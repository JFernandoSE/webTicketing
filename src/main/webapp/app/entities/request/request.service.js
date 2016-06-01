(function() {
    'use strict';
    angular
        .module('demoApp')
        .factory('Request', Request);

    Request.$inject = ['$resource', 'DateUtils'];

    function Request ($resource, DateUtils) {
        var resourceUrl =  'api/requests/:id';

        return $resource(resourceUrl, {}, {
            'byusers': {
                  method:'GET',
                  url: '/api/requests/users',
                  isArray: true
            },
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateRequest = DateUtils.convertLocalDateFromServer(data.dateRequest);
                        data.created_date = DateUtils.convertLocalDateFromServer(data.created_date);
                        data.last_modified_date = DateUtils.convertLocalDateFromServer(data.last_modified_date);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateRequest = DateUtils.convertLocalDateToServer(data.dateRequest);
                    data.created_date = DateUtils.convertLocalDateToServer(data.created_date);
                    data.last_modified_date = DateUtils.convertLocalDateToServer(data.last_modified_date);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateRequest = DateUtils.convertLocalDateToServer(data.dateRequest);
                    data.created_date = DateUtils.convertLocalDateToServer(data.created_date);
                    data.last_modified_date = DateUtils.convertLocalDateToServer(data.last_modified_date);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
