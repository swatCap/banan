$(function() {
    var availableTags = [
      "ActionScript",
      "AppleScript",
      "Asp",
      "BASIC",
      "C",
      "C++",
      "Clojure",
      "COBOL",
      "ColdFusion",
      "Erlang",
      "Fortran",
      "Groovy",
      "Haskell",
      "Java",
      "JavaScript",
      "Lisp",
      "Perl",
      "PHP",
      "Python",
      "Ruby",
      "Scala",
      "Scheme"
    ];
 
    $( "#country" )     
      .autocomplete({
        minLength: 0,
        source: 'http://127.0.0.1:9000//autocomplete//',
        messages: {
	        noResults: '',
	        results: function() {}
		    }
      });
  });