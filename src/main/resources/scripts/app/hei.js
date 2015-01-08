var conversations = [
{tweet: [
	{
		author: "Hanne",
		message: "Hei, dette er en første tweet!"
	},
	{
		author: "Øyvind",
		message: "Så tøft da!"
	},
	{
		author: "Hanne",
		message: "Itj sant?!"
	},
	{
		author: "Øyvind",
		message: "You rock!"
	}]}
]


var Tweet = React.createClass({
    render: function() {
        return <div className="tweet">
        Fra <strong>{this.props.author}</strong><br />
        {this.props.children}

        </div>;
    }
});

var TweetList = React.createClass({
    render: function() {
    	console.log(this.props.data);
    	var conversationNodes = conversations.map(function (tweets) {
    		var tweetNodes = tweets.tweet.map(function (tweet) {
				return <Tweet author={tweet.author} className="conversation">
					{tweet.message}
				</Tweet>;
			});

			return tweetNodes;
		});

		return <div>
		{ conversationNodes }
		</div>;
    }
});

var Conversation = React.createClass({
	render: function() {
		return <div className="conversation">
			<h3>Dette er en samtale</h3>
			<TweetList />
		</div>;
	}
});

React.render(<Conversation name="Twitter" />, $(".app")[0]);
 
