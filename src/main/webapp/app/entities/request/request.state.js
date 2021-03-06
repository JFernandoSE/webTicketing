(function() {
    'use strict';

    angular
        .module('demoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('request', {
            parent: 'entity',
            url: '/request?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.request.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/request/requests.html',
                    controller: 'RequestController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('request');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('request-detail', {
            parent: 'entity',
            url: '/request/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.request.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/request/request-detail.html',
                    controller: 'RequestDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('request');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Request', function($stateParams, Request) {
                    return Request.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('request.new', {
            parent: 'request',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request/request-dialog.html',
                    controller: 'RequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                dateRequest: null,
                                created_by: null,
                                created_date: null,
                                last_modified_by: null,
                                last_modified_date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('request', null, { reload: true });
                }, function() {
                    $state.go('request');
                });
            }]
        })
        .state('request.edit', {
            parent: 'request',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request/request-dialog.html',
                    controller: 'RequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Request', function(Request) {
                            return Request.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('request', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('request.delete', {
            parent: 'request',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request/request-delete-dialog.html',
                    controller: 'RequestDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Request', function(Request) {
                            return Request.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('request', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
