var gulp = require('gulp');
var less = require('gulp-less');
var server = require('gulp-server-livereload');
var watch = require('gulp-watch');

gulp.task('less', function () {
  return gulp.src('./less/**/*.less')
  .pipe(less())
  .pipe(gulp.dest('./css'));
});

gulp.task('serve', function () {
	gulp.src('')
    .pipe(server({
      livereload: {
        enable: true,
        filter: function(filePath, cb) {
          //if(/stats.js/.test(filePath)) {
          if(/css/.test(filePath)) {
            cb(true)
          } else if(/html/.test(filePath)){
            cb(true)
          // } else if(/src/.test(filePath)){
          //   cb(true)
          // } else if(/scss/.test(filePath)){
          //   cb(true)
          } 
        }
      },
      open: true
    }));
})

gulp.task('watch', function () {
	watch('less/**/*.less', function (event) {
    gulp.start('less');
  });
})

gulp.task('default', ['less', 'watch', 'serve']);