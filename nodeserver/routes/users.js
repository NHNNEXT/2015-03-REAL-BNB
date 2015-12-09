var express = require('express');
var router = express.Router();
var passport = require("passport")
var FacebookStrategy = require("passport-facebook").Strategy;

passport.use(new FacebookStrategy({
  clientID: 389047587948213,
  clientSecret: '68aa74fb543443fdb72a4936354779a7',
  callbackURL: "http://localhost:3000/users/facebook/callback"
},
function (accessToken, refreshToken, profile, done) {
  // User.findOrCreate(..., function(err, user) {
  //       if (err) { return done(err); }
  //       done(null, user);
  //     });
  console.log(accessToken)
  console.log(refreshToken)
  console.log(profile)
  console.log(done)
  done(null, profile);
}))

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

router.get('/facebook', passport.authenticate('facebook'));
router.get('/facebook/callback',
  passport.authenticate('facebook', { successRedirect: '/',
                                      failureRedirect: '/fail' }));
module.exports = router;
