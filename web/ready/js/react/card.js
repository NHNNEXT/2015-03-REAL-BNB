/*
 * - card-container
 *		- card-image
 *		- card-content
 * 		- card-action
 * 			- baby-chip(arr)
 *		- card-date
 */



var CommentBox = React.createClass({
  render: function() {
    return (
      <div className="commentBox">
        Hello, world! I am a CommentBox.
      </div>
    );
  }
});
React.render(
  <CommentBox />,
  document.getElementById('timeline_card')
);