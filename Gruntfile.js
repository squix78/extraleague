module.exports = function(grunt) {

    // 1. All configuration goes here 
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        livereloadx: {
            dir: 'src/main/webapp',
            proxy: 'http://localhost:8080/'
        },
        concat: {   
            dist: {
                src: [
                    'src/main/webapp/js/*.js'  // This specific file
                ],
                dest: 'src/main/webapp/dist/production.js',
            }
        },
        uglify: {
            build: {
                src: 'src/main/webapp/dist/production.js',
                dest: 'src/main/webapp/dist/production.min.js'
            }
        },
        watch: {
            scripts: {
                files: ['src/main/webapp/js/*.js'],
                tasks: ['concat', 'uglify'],
                options: {
                    spawn: false,
                },
            } 
        }

    });

    // 3. Where we tell Grunt we plan to use this plug-in.
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('livereloadx');
    

    // 4. Where we tell Grunt what to do when we type "grunt" into the terminal.
    grunt.registerTask('default', ['concat', 'uglify', 'livereloadx', 'watch']);

};