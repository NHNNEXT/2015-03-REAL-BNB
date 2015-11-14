var express = require('express');
var router = express.Router();
var passport = require('passport');

router.get('/login', function () {
	
})
/* GET users listing. */
router.post('/login', passport.authenticate('local', {
    successRedirect: '/',
    failureRedirect: 'user/login'
}));

module.exports = router;