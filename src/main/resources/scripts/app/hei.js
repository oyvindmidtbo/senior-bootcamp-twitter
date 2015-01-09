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
    	var conversationNodes = this.props.data.map(function (tweets) {
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
	addTweet: function(tweet) {
		var tweets = this.state.data;
		var newTweets = tweets.concat([tweet]);

		if(newTweets.length > 15) {
			newTweets.splice(0, 1);
		}
		this.setState({data: newTweets});
	},
	getInitialState: function() {
		return {data: []};
	},
	componentWillMount: function() {
		//var socket = io.connect();
		//var self = this;

		//socket.on('info', function (data) {
		//	self.addTweet(data.tweet);
		//});
		if (!window.WebSocket) {
			alert("FATAL: WebSocket not natively supported. This demo will not work!");
		}
		var ws;

		ws = new WebSocket("ws://localhost:9001");
		ws.onopen = function() {
			console.log("[WebSocket#onopen]\n");
		}
		ws.onmessage = function(e) {
			console.log("[WebSocket#onmessage] Message: '" + e.data + "'\n");
		}
		ws.onclose = function() {
			console.log("[WebSocket#onclose]\n");
			ws = null;
		}

		/*$("sendForm").observe("submit", function(e) {
			e.stop();
			if (ws) {
				var textField = $("textField");
				ws.send(textField.value);
				console.log("[WebSocket#send]      Send:    '" + textField.value + "'\n");
				textField.value = "";
				textField.focus();
			}
		});
		$("disconnect").observe("click", function(e) {
		e.stop();
		if (ws) {
			ws.close();
			ws = null;
		}*/
	},
	render: function() {
		return <div className="conversation">
			<h3>Dette er en samtale</h3>
			<TweetList data={this.state.data} />
		</div>;
	}
});

React.render(<Conversation name="Twitter" />, $(".app")[0]);
 
