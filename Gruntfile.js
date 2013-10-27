'use strict';
var LIVERELOAD_PORT = 35729;
var lrSnippet = require('connect-livereload')({port: LIVERELOAD_PORT});
var proxySnippet = require('grunt-connect-proxy/lib/utils').proxyRequest;
var mountFolder = function (connect, dir) {
    return connect.static(require('path').resolve(dir));
};

// # Globbing
// for performance reasons we're only matching one level down:
// 'test/spec/{,*/}*.js'
// use this if you want to recursively match all subfolders:
// 'test/spec/**/*.js'

module.exports = function (grunt) {
    // load all grunt tasks
    require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

    grunt.initConfig({
        watch: {
            options: {
                nospawn: true
            },
            less: {
                files: ['src/main/webapp/css/*.less'],
                tasks: ['less:server']
            },
            livereload: {
                options: {
                    livereload: LIVERELOAD_PORT,
                    middleware: function (connect) {
                        return [
                            proxySnippet
                        ];
                    }
                },
                files: [
                    'src/main/webapp/*.html',
                    'src/main/webapp/css/{,*/}*.css',
                    'src/main/webapp/js/{,*/}*.js',
                    'src/main/webapp/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}'
                ]
            }
        },
        connect: {
            options: {
                port: 9000,
                // change this to '0.0.0.0' to access the server from outside
                hostname: 'localhost',
                base: 'src/main/webapp/'
            },
            proxies: [
                      {
                          context: '/rest',
                          host: 'localhost',
                          port: 8080,
                          https: false,
                          changeOrigin: false,
                          xforward: false
                      }
                  ],
            livereload: {
                options: {
                    middleware: function (connect) {
                        return [
                            mountFolder(connect, 'src/main/webapp'),
                            proxySnippet ,
                            lrSnippet
                        ];
                    }
                }
            }
        },
        open: {
            server: {
                path: 'http://localhost:<%= connect.options.port %>'
            }
        },
        less: {
            server: {
                options: {
                    paths: ['src/main/webapp/components/bootstrap/less', 'app/styles']
                },
                files: {
                    'src/main/webapp/css/main.css': 'src/main/webapp/css/main.less'
                }
            }
        },
    });

    grunt.registerTask('server', function (target) {

        grunt.task.run([
            'less:server',
            'configureProxies',
            'connect:livereload',
            'open',
            'watch'
        ]);
    });
};
