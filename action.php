<!DOCTYPE html>
<html>
<head>
	<style>
	  .bordered {
		width: 1100px;
		height: 120px;
		padding: 20px;
		border: 20px;
		border-radius: 10px;
		  }
		  
	  body { 
		background: url(image.jpg) no-repeat fixed center; 
		background-size: 100% 100%;
  	}
		
	</style>
	<title>actions</title>
</head>


<body>

<?php
	
	$data = $_POST['query'];
	$data=urlencode($data);
	$url ="http://localhost:8983/solr/gettingstarted/select?indent=on&q=".$data."&wt=json";
	$response = file_get_contents($url);
	/*echo '<pre>';
	echo print_r($response,1);
	echo '</pre>';*/

	$json = json_decode($response, true);

	/*echo '<pre>';
	echo print_r($json,1);
	echo '</pre>';

	echo '<pre>';
		print_r(array_values($json));
	echo '</pre>';*/

	
	echo '<pre>';	
	echo "<h2 style='color:red'>Action Results: </h2><br>";
		foreach($json['response']['docs'] as $item){
			$data = array($item['Rank'],$item['Song'],$item['Artist'],$item['Year'],$item['Lyrics']);
			echo '<a target="_blank" href="result.php?value='.$item['id'].'">';
			echo '<div class="bordered" style="background-color: seashell;color:black">';	
			echo "Rank: ".$data[0][0]."<br>Song: ".$data[1][0].", <br>Artist: ".$data[2][0].", <br>Year: ".$data[3][0].", <br>Lyrics: ".trim_text($data[4][0],130)."<br>";
			echo "</div></a><br><br><br>"; 
		}
	echo '</pre>';
	
	
	function trim_text($input, $length, $ellipses = true, $strip_html = true) {
    //strip tags, if desired
    if ($strip_html) {
        $input = strip_tags($input);
    }
  
    //no need to trim, already shorter than trim length
    if (strlen($input) <= $length) {
        return $input;
    }
  
    //find last space within length
    $last_space = strrpos(substr($input, 0, $length), ' ');
    $trimmed_text = substr($input, 0, $last_space);
  
    //add ellipses (...)
    if ($ellipses) {
        $trimmed_text .= '...';
    }
  
		return $trimmed_text;
	}
	
?>


</body>
</html>
