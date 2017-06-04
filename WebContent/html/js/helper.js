if (!String.prototype.repeat) {
	String.prototype.repeat = function(n) {
		n = n || 1;
		return Array(n + 1).join(this);
	}
}

String.prototype.fill = function(str, toLen) {
	if (this.length >= toLen) {
		return this;
	} else {
		str = str || " ";
		var fillLen = toLen - this.length;
		var reps = fillLen / str.length;
		var remainder = fillLen % str.length;
		return str.repeat(reps) + str.substring(0, remainder) + this;
	}
}
