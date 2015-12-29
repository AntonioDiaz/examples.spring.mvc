<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html lang="en">
<head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-smart-table/2.1.6/smart-table.js"></script>
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<meta charset="utf-8">
	<title>Customers - List</title>
	<script type="text/javascript">
		$(document).ready(function() {});
		var app = angular.module('myApp', [ 'smart-table' ]);
		app.controller('paginationCtrl', ['$scope','$http', '$window',
			function($scope, $http, $window) {
				$scope.itemsByPage = 10;
				$scope.rowCollection = [];
				$scope.displayedCollection = [].concat($scope.rowCollection);
				$scope.search = function doSearch() {
					var params = {
						params : {
							"id" : $scope.filterId,
							"name" : $scope.filterName,
							"surname01" : $scope.filterSurname01,
							"surname02" : $scope.filterSurname02
						}
					};
					$http.get("doSearch", params).success(function(response) {
						console.log("response ->" + response);
						console.log("response ->" + response.length);
						$scope.rowCollection = response;
					})					
				}
				$scope.queryOrders = function doSearch(myCustomer) {
					console.log(myCustomer);
					console.log(myCustomer.id);
					var params = {
							params : {
								"idCustomer" : myCustomer.id,
							}
						};
					$http.get("viewOrders", params).success(function(response) {
						console.log("response ->" + response);
						console.log("response ->" + response.length);
						var newHtml = "";
						for (i=0; i<response.length; i++) {
							newHtml += response[i].name + "<br>";							
						}
						$('#modalContent').html(newHtml);
						$('#myModal').modal('show')
					})					
				}
			}	
		]);		
	</script>
</head>
<body ng-app="myApp">
	<div class="container">
	<h3>Customers</h3>
	<form ng-submit="search()" ng-controller="paginationCtrl" class="form-inline" >
		<div class="form-group">
			<label for="name">Id</label> <input type="number" class="form-control" id="id" ng-model="filterId">
		</div>
		<div class="form-group">
			<label for="name">Name</label> <input type="text" class="form-control" id="name" ng-model="filterName">
		</div>
		<div class="form-group">
			<label for="name">Surname_01</label> <input type="text" class="form-control" id="surname01" ng-model="filterSurname01">
		</div>
		<div class="form-group">
			<label for="name">Surname_02</label> <input type="text" class="form-control" id="surname02" ng-model="filterSurname02">
		</div>
		<div class="form-group">
			<button type="button" class="btn btn-default" ng-click="search()">Search</button>
		</div>	
		<hr>
		<table st-table="displayedCollection" st-safe-src="rowCollection" class="table table-striped">
			<thead>
				<tr>
					<th st-sort="id">Id</th>
					<th st-sort="name">Name</th>
					<th st-sort="surname01">Surname 01</th>
					<th st-sort="surname02">Surname 02</th>
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="row in displayedCollection">
					<td>{{row.id}}</td>
					<td>{{row.name}}</td>
					<td>{{row.surname01}}</td>
					<td>{{row.surname02}}</td>
					<td align="center">
						<button type="button" ng-click="queryOrders(row)" class="btn btn-md btn-warning">
							<i class="glyphicon glyphicon-eye-open"> </i>&nbsp;View Orders
						</button>
					</td>
				</tr>
			</tbody>		
			<tfoot>
				<tr>
					<td colspan="5" class="text-center">
						<div st-pagination="" st-items-by-page="itemsByPage" st-displayed-pages="7"></div>
					</td>
				</tr>
			</tfoot>
		</table>
	</form>
	<br>
	<hr>
	
	
	<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Modal Header</h4>
      </div>
      <div class="modal-body">
        <div id="modalContent"></div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>
	
	
    </div>
</body>
</html>
