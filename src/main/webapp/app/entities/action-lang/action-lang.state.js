(function() {
    'use strict';

    angular
        .module('demoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('action-lang', {
            parent: 'entity',
            url: '/action-lang?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'demoApp.actionLang.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/action-lang/action-langs.html',
                    controller: 'ActionLangController',
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
                    $translatePartialLoader.addPart('actionLang');
                    $translatePartialLoader.addPart('language');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('action-lang-detail', {
            parent: 'entity',
            url: '/action-lang/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.actionLang.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/action-lang/action-lang-detail.html',
                    controller: 'ActionLangDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('actionLang');
                    $translatePartialLoader.addPart('language');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ActionLang', function($stateParams, ActionLang) {
                    return ActionLang.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('action-lang.new', {
            parent: 'action-lang',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/action-lang/action-lang-dialog.html',
                    controller: 'ActionLangDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                languageCode: null,
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('action-lang', null, { reload: true });
                }, function() {
                    $state.go('action-lang');
                });
            }]
        })
        .state('action-lang.edit', {
            parent: 'action-lang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/action-lang/action-lang-dialog.html',
                    controller: 'ActionLangDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ActionLang', function(ActionLang) {
                            return ActionLang.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('action-lang', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('action-lang.delete', {
            parent: 'action-lang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/action-lang/action-lang-delete-dialog.html',
                    controller: 'ActionLangDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ActionLang', function(ActionLang) {
                            return ActionLang.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('action-lang', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
