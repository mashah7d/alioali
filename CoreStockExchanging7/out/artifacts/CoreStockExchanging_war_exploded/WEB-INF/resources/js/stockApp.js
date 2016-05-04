(function () {
    var stockApp = angular.module("stockApp", []);

    var customerController = function ($scope, $http) {
        var showInfo = function (response) {
            $scope.info = response.data;
        };

        var onError = function (reason) {
            $scope.error = "Sorry!";
        };

        $scope.id = 1;

        var getCustomerInfo = function () {
            $http.get("http://localhost:8080/rest/customer/" + $scope.id).then(showInfo, onError);
        };
        getCustomerInfo();

        var updateCustomerCredit = function(response){
          $scope.info.credit = response.data.currentCredit;
        };

        $scope.depositCredit = function (amount) {
            var depositCreditData = $.param({
                "userId": $scope.info,
                "amount": amount
            });
            $http.put("http://localhost:8080/rest/customer/deposit/",
                depositCreditData).then(updateCustomerCredit, onError);
        };

        $scope.getCustomerInfo = getCustomerInfo;
    };

    stockApp.controller("customerController", customerController);
})();