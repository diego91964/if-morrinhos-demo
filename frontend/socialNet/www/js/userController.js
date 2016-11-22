app.controller('UserCtrl', function($scope, $http) {

	$scope.dadosUsuario = {};



   // Perform the login action when the user submits the login form
   $scope.cadastrar = function() {
   	
   		console.log('Cadastrando Usuário', $scope.dadosUsuario);
   		$http.get('http://localhost:8080/user/cadastrarNovoUsuario/'
   		+ $scope.dadosUsuario.nome + '/' + $scope.dadosUsuario.email + '/' + $scope.dadosUsuario.senha)
   		.success(function(data, status, headers,config){
   			console.log('data success');
      		console.log(JSON.stringfy(data)); // for browser console
      		$scope.result = data; // for UI
		  }).error(function(data, status, headers,config){
   				console.log('data error');
   	 	}).then(function(result){
   			//things = result.data;
   		});


   };

   $scope.result = "";




});