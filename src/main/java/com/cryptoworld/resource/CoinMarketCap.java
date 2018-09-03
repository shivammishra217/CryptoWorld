package com.cryptoworld.resource;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cryptoworld.model.UserProfile;
import com.cryptoworld.model.CoinMarketCap.Cryptocurrency;
import com.cryptoworld.model.CoinMarketCap.CryptocurrencyData;
import com.cryptoworld.model.CoinMarketCap.CurrencyChangeData;
import com.cryptoworld.service.CoinMarketCapService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


@RestController
public class CoinMarketCap {


	@Autowired
	CoinMarketCapService coinMarketCapService;
		
	
	@GetMapping("/coinMarketCapDataObject")
	public ArrayList<HashMap<String, String>> coinMarketCapDataObject(Principal principal) throws Exception { 
		
		System.out.println("in the coinMarketCapDataObject method");
		
		if(principal==null)
			throw new Exception("Principal cannot be null at home page : User is not logged in.");

		//if(true)
		//	throw new AccessDeniedException("User is not logged in.");
		
		//UserProfile userProfile = (UserProfile)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ModelAndView mv = new ModelAndView("coinMarketCap");
		
		CryptocurrencyData coinMarketCapFullData  = null;
		
		try {
			coinMarketCapFullData = coinMarketCapService.getAllTickerData();
		}
		catch(Exception ex) {
			System.out.println("CoinMarketCapService is not responsding");
		}
		finally {
			
			return convertToCoinMarketCapListData(coinMarketCapFullData);
			
			
		}
		
	}
	
		
	private  ArrayList<HashMap<String, String>> convertToCoinMarketCapListData(CryptocurrencyData coinMarketCapFullData) {
		
		if(coinMarketCapFullData == null)
			return null;

		ArrayList<HashMap<String, String>> coinMarketCapList = new ArrayList<>();
		
		HashMap<String, Cryptocurrency> data = coinMarketCapFullData.getData();
		
		data.forEach((key,cryptocurrency)-> {
			
			HashMap<String, String> hashmap = new HashMap<>();
			
			int id = cryptocurrency.getId();
			int rank = cryptocurrency.getRank();
			String name = cryptocurrency.getName();
			long circulating_supply = cryptocurrency.getCirculating_supply();
			
			//hashmap.put("id",Integer.toString(id));
			hashmap.put("rank",Integer.toString(rank));
			hashmap.put("name",name);
			hashmap.put("circulating_supply", Long.toString(circulating_supply));
			
			HashMap<String,CurrencyChangeData> quotes = cryptocurrency.getQuotes();
			
			quotes.forEach((key1,currencyChangeData)-> {
				
				long volume_24h = currencyChangeData.getVolume_24h();
				float price = currencyChangeData.getPrice();
				long market_cap  = currencyChangeData.getMarket_cap();
				double percent_change_24h = currencyChangeData.getPercent_change_24h();
				
				hashmap.put("volume_24h", Long.toString(volume_24h));
				hashmap.put("price", Float.toString(price));
				hashmap.put("market_cap", Long.toString(market_cap));
				hashmap.put("percent_change_24h", Double.toString(percent_change_24h));
				
			});
			
			coinMarketCapList.add(hashmap);
		});

		return coinMarketCapList;
		
	}

	@GetMapping("/api/open/getalldata")
	public ArrayList<HashMap<String, String>> exposeData(){
		
		CryptocurrencyData coinMarketCapFullData  = coinMarketCapService.getAllTickerData();;
		ArrayList<HashMap<String, String>> cryptoWorldModel = convertToCoinMarketCapListData(coinMarketCapFullData);
		return cryptoWorldModel;

	}
	
	@GetMapping("/api/secured/getalldata")
	public ArrayList<HashMap<String, String>> exposeDataSecured(WebRequest webrequest){
		
		CryptocurrencyData coinMarketCapFullData  = coinMarketCapService.getAllTickerData();;
		ArrayList<HashMap<String, String>> cryptoWorldModel = convertToCoinMarketCapListData(coinMarketCapFullData);
		return cryptoWorldModel;

	}
	
	@GetMapping("/api/runtimeexception")
	public void runtimeException(){		
		throw new RuntimeException();
	}
	
	@GetMapping("/api/accessdeniedexception")
	public void accessDeniedException(){		
		throw new AccessDeniedException("Access denied");
	}
	
	
}
