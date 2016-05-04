<%--
  Created by IntelliJ IDEA.
  User: Ali
  Date: 05/03/2016
  Time: 01:02 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html ng-app="stockApp">
  <head>
    <title>سامانه ی بورس</title>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/bootstrap-rtl.min_.css" />"/>

    <script src="<c:url value="/resources/js/angular.js" />"></script>
    <script src="<c:url value="/resources/js/stockApp.js" />"></script>
  </head>

  <body class="container-fluid">
    <div class="navigation col-md-4">

    </div>
    <div class="main col-md-8" ng-controller="customerController">

      <form name="idSelect">
        <input type="text" ng-model="id"/>
        <input type="submit" ng-click="getCustomerInfo(id)"/>
      </form>

      <div class="personalInfo">
        <p class="name row">{{info.name}}</p>
        <p class="family row">{{info.family}}</p>
        <p class="credit row">{{info.credit | currency}}</p>
        <p class="error">{{error}}</p>
      </div>
      <div class="updateCredit row">
        <form name="depositForm">
          <input type="text" ng-model="amount"/>
          <input type="submit" value="درخواست" ng-click="depositCredit(amount)"/>
        </form>
      </div>

      <table class="sharesTable table">
        <tr>
          <th>نماد</th>
          <th>تعداد</th>
        </tr>
        <tr ng-repeat="share in info.shares">
          <td>{{share.symbol}}</td>
          <td>{{share.quantity}}</td>
        </tr>
      </table>


      <table class="buyOrdersTable table">
        <tr>
          <th>قیمت</th>
          <th>تعداد</th>
        </tr>
        <tr ng-repeat="buyOrder in info.buyOrdersQueue">
          <td>{{buyOrder.price}}</td>
          <td>{{buyOrder.quantity}}</td>
        </tr>
      </table>

      <table class="sellOrdersTable table">
        <tr>
          <th>قیمت</th>
          <th>تعداد</th>
        </tr>
          <tr ng-repeat="sellOrder in info.sellOrdersQueue">
            <td>{{sellOrder.price}}</td>
            <td>{{sellOrder.quantity}}</td>
          </tr>
      </table>
    </div>
  </body>
</html>