package com.iktpreobuka.restexamples.controllers;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.restexamples.entities.BankClientEntity;

@RestController
@RequestMapping("/bankclients")
public class BankClientRestController {
	
	private Map<String, String> recnik = new HashMap<>();
	public BankClientRestController() {
    recnik.put("racunar", "computer");
    recnik.put("programiranje", "programming");
    recnik.put("baza podataka", "database");
    recnik.put("mreza", "network");
    recnik.put("server", "server"); 
    recnik.put("softver", "software");
    recnik.put("hardver", "hardware");
    recnik.put("aplikacija", "application");
	}
    
    @RequestMapping("/recnik")
    public String prevodiRec(@RequestParam String rec) {
       
        return recnik.getOrDefault(rec, "Rec " + rec + " ne postoji u recniku.");
    }
	@RequestMapping(method=RequestMethod.GET, value = "/greetings/{name}")
    public String greetByName(@PathVariable String name) {
        return "Hello " + name + "!";
    }
	
	@RequestMapping(method=RequestMethod.GET, value = "/sumaNiza/{n}")
	public String sumaPrvihNBrojeva(@PathVariable int n) {
        int suma = n * (n + 1) / 2;
        return "Suma prvih " + n + " prirodnih brojeva je: " + suma;
    }
	
	
	protected List<BankClientEntity> getDB() {
		List<BankClientEntity> clients = new ArrayList<BankClientEntity>();
		clients.add(new BankClientEntity(1, "Milan", "Celikovic",
		"milancel@uns.ac.rs", LocalDate.of(1985, 8, 15)));
		clients.add(new BankClientEntity(2, "Vladimir", "Dimitrieski",
		"dimitrieski@uns.ac.rs", LocalDate.of(1990, 8, 24)));
		return clients;
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/{clientId}")
	public BankClientEntity getById(@PathVariable String clientId) {
		for(BankClientEntity bcb : getDB()) {
		if(bcb.getId().equals(Integer.parseInt(clientId)))
		return bcb;
		}
		return new BankClientEntity();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String add(@RequestBody BankClientEntity client) {
		System.out.println(client.getName().concat(" ").concat(client.getSurname()));
		return "New client added";
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{clientId}")
	public BankClientEntity modify(@PathVariable String clientId, @RequestBody BankClientEntity client) {
		BankClientEntity bcb = new BankClientEntity(1, "Milan", "Celikovic", "milancel@uns.ac.rs", LocalDate.of(1985, 8, 15));
		if(clientId.equals("1")) {
			bcb.setName(client.getName());
			return bcb;
		} else
			return null;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{clientId}")
	public BankClientEntity delete(@PathVariable String clientId) {
		for(BankClientEntity bcb : getDB()) {
			if(bcb.getId().equals(Integer.parseInt(clientId))) {
				getDB().remove(bcb);
				return bcb;
			}
		}
		return new BankClientEntity();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/client")
	public BankClientEntity getByNameSurname(@RequestParam("name") String name, @RequestParam("surname") String surname) {
		if(name.equals("Milan") && surname.equals("Celikovic")) {
			return new BankClientEntity(1, "Milan", "Celikovic", "milancel@uns.ac.rs", LocalDate.of(1985, 8, 15));
		}
		else {
			return new BankClientEntity();
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/clients/{firstletter}")
	public List<String> getByFirstLetter(@PathVariable String firstletter) {
		List<String> names = new ArrayList<String>();
		List<BankClientEntity> clients = getDB();
		
		for(BankClientEntity bcb : clients) {
			String name = bcb.getName();
			if(name.startsWith(firstletter)) {
				names.add(name);
			}
		}
		return names;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/clients/firstletters")
	public List<String> getByFirstLetters(@RequestParam("firstletterName") String firstletterName, @RequestParam("firstletterSurname") String firstletterSurname) {
		List<String> clients = new ArrayList<String>();
		
		for(BankClientEntity bcb : getDB()) {
			
			if(bcb.getName().startsWith(firstletterName)&&bcb.getSurname().startsWith(firstletterSurname)) {
				clients.add(bcb.getName().concat(" ").concat(bcb.getSurname()));
			}
		}
		return clients;
	}
	@RequestMapping(method = RequestMethod.GET, value ="/emails")
	public List<String> getEmails(){
		
		List<String> emails = new ArrayList<String>();
		for(BankClientEntity bcb : getDB()) {
			emails.add(bcb.getEmail());
		}
		return emails;
	}
	

	
    @RequestMapping(method = RequestMethod.GET, value = "/clients/sort/{order}")
    public List<String> getSortedClients(@PathVariable String order) {
        List<BankClientEntity> clients = getDB();
        List<String> clientNames = new ArrayList<>();
        
        for (BankClientEntity client : clients) {
            clientNames.add(client.getName());
        }

        if("asc".equalsIgnoreCase(order)) {
            clientNames.sort(String::compareTo);
        } else if("desc".equalsIgnoreCase(order)) {
            clientNames.sort((name1, name2) -> name2.compareTo(name1));
        } else {
            clientNames.clear();
            clientNames.add("Invalid sort order, use 'asc' or 'desc'.");
        }
        
        return clientNames;
    }
    @RequestMapping(method = RequestMethod.GET, value = "/clients/bonitet")
    public List<BankClientEntity> updateClientBonitet() {
        List<BankClientEntity> clients = getDB();
        for (BankClientEntity client : clients) {
            LocalDate today = LocalDate.now();
            LocalDate birthDate = client.getDateOfBirth();
            int age = Period.between(birthDate, today).getYears();
            
            if(age < 65) {
                client.setBonitet('P'); 
            } else {
                client.setBonitet('N'); 
            }
        }
        return clients; 
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/clients/delete")
    public String deleteClientsWithMissingInfo() {
        List<BankClientEntity> clients = getDB();
        boolean removed = clients.removeIf(client -> client.getName() == null || client.getSurname() == null || client.getEmail() == null);
        if (removed) {
            return "Clients with missing information have been removed.";
        } else {
            return "No clients with missing information were found.";
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/countLess/{years}")
    public int countClientsYoungerThan(@PathVariable int years) {
        List<BankClientEntity> clients = getDB();
        int count = 0;
        for (BankClientEntity client : clients) {
            if (client.getDateOfBirth() != null) { 
                Period age = Period.between(client.getDateOfBirth(), LocalDate.now()); // racuna starost
                if (age.getYears() < years) { 
                    count++;
                }
            }
        }
        return count; // vraca mladje od odredjene starosti
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/changelocation/{clientId}")
    public BankClientEntity changeLocation(@PathVariable Integer clientId, @RequestParam("grad") String noviGrad) {
        List<BankClientEntity> clients = getDB();
        for (BankClientEntity client : clients) {
            if (client.getId().equals(clientId)) {
                client.setGrad(noviGrad);
                return client; //azuriran klijent
            }
        }
        return null; // null ako nije pronadjen
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/from/{city}")
    public List<BankClientEntity> getClientsFromCity(@PathVariable String city) {
        List<BankClientEntity> filteredClients = new ArrayList<>();
        for (BankClientEntity client : getDB()) {
            if (city.equals(client.getGrad())) {
                filteredClients.add(client);
            }
        }
        return filteredClients;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/averageYears")
    public double calculateAverageAge() {
        List<BankClientEntity> clients = getDB();
        if (clients.isEmpty()) {
            return 0; // ako nema klijenata
        }
        
        double totalYears = 0;
        int count = 0;
        
        for (BankClientEntity client : clients) {
            if (client.getDateOfBirth() != null) { 
                Period age = Period.between(client.getDateOfBirth(), LocalDate.now()); // racuna starost
                totalYears += age.getYears(); 
                count++;
            }
        }
        
        if (count == 0) {
            return 0; // ako nema klijenata sa tim datumom
        }
        
        return totalYears / count; // prosek godina
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/findByCityAndAge")
    public List<BankClientEntity> findByCityAndAge(@RequestParam String grad, @RequestParam int maxAge) {
        LocalDate now = LocalDate.now();
        List<BankClientEntity> result = new ArrayList<>();
        for (BankClientEntity client : getDB()) {
            LocalDate dob = client.getDateOfBirth(); // pretpostavka getDateOfBirth vraća LocalDate
            int age = Period.between(dob, now).getYears();
            if (grad.equals(client.getGrad()) && age < maxAge) {
                result.add(client);
            }
        }
        return result;
    }
    
	@RequestMapping(method = RequestMethod.GET)
	public List<BankClientEntity> getAll() {
		List<BankClientEntity> clients = new ArrayList<BankClientEntity>();
		clients.add(new BankClientEntity(1, "Milan", "Celikovic",
		"milancel@uns.ac.rs", LocalDate.of(1990, 5, 24) ));
		clients.add(new BankClientEntity(2, "Vladimir", "Dimitrieski",
		"dimitrieski@uns.ac.rs", LocalDate.of(1985, 8, 15)));
		return clients;
	}
}
