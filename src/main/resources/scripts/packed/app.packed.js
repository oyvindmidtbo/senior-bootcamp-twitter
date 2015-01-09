var ReactCSSTransitionGroup = React.addons.CSSTransitionGroup;

var Tweet = React.createClass({
    render: function() {
        return <div className="tweet" key={this.props.keyToAnimate}>
				<div className="panel panel-default">
					<div className="panel-body">
						{this.props.children}
					</div>
					<div className="panel-footer">Retweets: {this.props.conversationSize}</div>
				</div>
			</div>;
    }
});

var TweetList = React.createClass({
    render: function() {
    	var tweetNodes = this.props.data.map(function (tweet) {
    		try {
    			tweet = $.parseJSON(tweet);
    		}

    		catch (e){
    			//HAHA, nei.
    			return;
    		}
			return <Tweet keyToAnimate="{tweet.id}" author={tweet.author} conversationSize={tweet.size} className="conversation">
						{tweet.text}
					</Tweet>;
		});

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

		if(newTweets.length > 2000) {
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
				//console.log("Message: '" + e.data + "'\n");
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
 
