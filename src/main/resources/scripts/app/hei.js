var ReactCSSTransitionGroup = React.addons.CSSTransitionGroup;

var Tweet = React.createClass({
    render: function() {
        return <div className="tweet" key={this.props.keyToAnimate}>
				<div className="panel panel-default">
					<div className="panel-body">
						Fra <strong>{this.props.author}</strong><br />
						Melding: {this.props.children}
					</div>
					<div className="panel-footer">Replies: 5</div>
				</div>
			</div>;
    }
});

var TweetList = React.createClass({
    render: function() {
    	var tweetNodes = this.props.data.map(function (tweet) {
				return <Tweet keyToAnimate="{tweet}" author={tweet.author} className="conversation">
					{tweet}
				</Tweet>;
		});

		/*
			var conversationNodes = this.props.data.map(function (tweets) {
    		var tweetNodes = tweets.tweet.map(function (tweet, index) {
    			console.log(index);
				return <Tweet author={tweet.author} position="{index}" className="conversation">
					{tweet.message}
				</Tweet>;
			});

			return tweetNodes;
		});
		*/

		return <ReactCSSTransitionGroup transitionName="example">
			{ tweetNodes }
		</ReactCSSTransitionGroup>;
    }
});

var Conversation = React.createClass({
	getInitialState: function() {
		return {data: []};
	},
	addTweet: function(tweet) {
		var tweets = this.state.data;
		var newTweets = tweets.concat([tweet]);

		if(newTweets.length > 20) {
			newTweets.splice(0, 1);
		}
		this.setState({data: newTweets});
	},
	componentWillMount: function() {
		this.setState({data: []});

		var ws;
		var that = this;

		var stopWebsocket = function() {
			ws.close();
			ws = null;
			console.log("WebSocket connection closed");
		};

		var startWebsocket = function() {
			if (!window.WebSocket) {
				alert("FATAL: WebSocket not natively supported :(");
			}

			ws = new WebSocket("ws://localhost:8887");
			ws.onopen = function() {
				console.log("WebSocket connection opened");
			}
			ws.onmessage = function(e) {
				console.log("Message: '" + e.data + "'\n");
				that.addTweet(e.data);
			}
		};
		
		$(".stop").click(function(e) {
			stopWebsocket();
			$(".start, .stop").toggleClass("hidden");
		});

		$(".start").click(function(e) {
			startWebsocket();

			$(".start, .stop").toggleClass("hidden");
		});


		startWebsocket();
	},

	render: function() {
		return <div className="conversation">
			<TweetList data={this.state.data} />
		</div>;
	}
});

React.render(<Conversation name="Twitter" />, $(".app")[0]);
 
