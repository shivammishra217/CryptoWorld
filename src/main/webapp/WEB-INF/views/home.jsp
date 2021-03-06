<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "spring" uri = "http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cryptocurrency List</title>


<spring:url value="../resources/DataTables/datatables.css" var="datatablesCssUrl"/>
<spring:url value="../resources/DataTables/datatables.js" var="datatablesJsUrl"/>
<spring:url value="../resources/jquery-ui.js" var="jqueryUiJsUrl"/>
<spring:url value="../resources/cryptocurrencyList/cryptocurrencyList.js" var="cryptocurrencyListJsUrl"/>
<spring:url value="../resources/cryptocurrencyList/cryptocurrencyList.css" var="cryptocurrencyListCssUrl"/>

<%@ include file="commonFiles.jsp" %>

<link href="${datatablesCssUrl}" rel="stylesheet">
<link href="${cryptocurrencyListCssUrl}" rel="stylesheet">
<script type="text/javascript" src="${jqueryUiJsUrl}"></script>
<script type="text/javascript" src="${cryptocurrencyListJsUrl}"></script>
<script type="text/javascript" src="${datatablesJsUrl}"></script>



</head>
<body>

 <%@ include file="navigation.jsp" %>

<table id="fulldata" border="2" class="row-border hover order-column" style="width:100%">

	<thead>
		<tr>
			<th>#</th>
			<th>Name</th>
			<th>Market Cap (USD)</th>
			<th>Price (USD)</th>
			<th>Volume (24h)(USD) </th>
			<th>Circulating Supply</th>
			<th>Change (24h) </th>
		</tr>	
	</thead>
	
	<tbody>

		<c:forEach items="${coinMarketCapFullData.data}" var="entry">
			
			<tr>
				<td>${entry.value.id}</td>
				<td>${entry.value.name}</td>
				<td>${entry.value.quotes.USD.market_cap}</td>
				<td>${entry.value.quotes.USD.price }</td>
				<td>${entry.value.quotes.USD.volume_24h}</td>
				<td>${entry.value.circulating_supply}</td>
				<td>${entry.value.quotes.USD.percent_change_24h}</td>
			</tr>
				
		</c:forEach>
		
	</tbody>

</table>


</body>
</html>