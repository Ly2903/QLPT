<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="<c:url value ='/resources/js/main.js'/>"
	type="text/javascript"></script>
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	// Load google charts
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	google.charts.setOnLoadCallback(drawChart);

	// Draw the chart and set the chart values
	function drawChart() {
		var data = google.visualization.arrayToDataTable([
				[ "Task", "Hours per Day" ], [ "Đã Thuê", ${slPhongDaThue} ],
				[ "Phòng Trống", ${slPhongTrong} ], ]);

		// Optional; add a title and set the width and height of the chart
		var options = {
			title : "Biểu đồ trạng thái của dãy trọ",
			width : 550,
			height : 400
		};

		// Display the chart inside the <div> element with id="piechart"
		var chart = new google.visualization.PieChart(document
				.getElementById("piechart"));
		chart.draw(data, options);
	}
	/* Calendar in electricity */
    $(document).ready(function(){
      var date_input=$('input[name="date"]'); //our date input has the name "date"
      var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
      var options={
        format: 'mm/yyyy',
        container: container,
        todayHighlight: true,
        autoclose: true,
      };
      date_input.datepicker(options);
    })
   
    
</script>
<body></body>

<html></html>