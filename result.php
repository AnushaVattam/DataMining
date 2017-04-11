/*Anusha Vattam*/
<?php 
	ini_set('session.gc_maxlifetime', 3500);
	session_start();
?>
<html>
<head>
<title>Results</title>
<style>
	body { 
    background: url(img.jpg) no-repeat fixed center; 
	background-size: 100% 100%;
	}
</style>
</head>
<body>
	<div align="center">
			<h1>Results</h1>
		
		<?php 
		$id = $_GET['value'];
		$url = "http://localhost:8983/solr/gettingstarted/get?id=".$id."&wt=json";
		$data = file_get_contents($url);
		$json = json_decode($data, true);

		echo '<pre>';	
			echo '<pre>';
			echo "<h4>";
				echo "Rank: "; print_r($json['doc']['Rank'][0]);   echo "<br>";
				echo "Song: "; print_r($json['doc']['Song'][0]);   echo "<br>";
				echo "Artist: "; print_r($json['doc']['Artist'][0]); echo "<br>";
				echo "Year: "; print_r($json['doc']['Year'][0]);     echo "<br>";				
				$newtext = wordwrap($json['doc']['Lyrics'][0], 100, "<br />");
				echo "Lyrics: ".$newtext;
			echo "</h4>";
			echo '</pre>';
		
		?>

	</center>
</body>
</html>