package com.skerdy.ruleengine;

import com.skerdy.ruleengine.core.exception.InvalidNodeConnectionException;
import com.skerdy.ruleengine.core.manager.ChainManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class RuleEngineApplication {

	private final ChainManager chainManager;

	private int interval = 0;
	private int sum = 0;
	private int max = 0;

	public RuleEngineApplication(ChainManager chainManager) {
		this.chainManager = chainManager;
	}

	public static void main(String[] args) {
		SpringApplication.run(RuleEngineApplication.class, args);
	}

	@PostConstruct
	public void handlePost(){
		try {
			this.chainManager.init();
		} catch (InvalidNodeConnectionException e) {
			e.printStackTrace();
		}
//		this.chainManager.startChain("5cf5334835a5d07adb6360bd");
//		this.chainManager.getChains().get(1).getSourceNode().getChannel().buffer(Duration.ofSeconds(1L)).doOnNext(messages -> {
//			sum+= messages.size();
//			if(messages.size()>max){
//				max = messages.size();
//			}
//			System.out.println("Interval ==> " + interval++ + " Size ==> " + messages.size()  + " Total Arrived => " + sum + " Maximum/Second ==> " + max);
//		}).subscribe();
//		((DuplexNode<? extends NodeConfiguration>)this.chainManager.getChains().get(0).getNode(1)).getOutputChannel().subscribe(message -> {
//			System.out.println("Node 1 ==> " + message.toString());
//		});
//		((SimpleNode<? extends NodeConfiguration>)this.chainManager.getChains().get(0).getNode(2)).getChannel().subscribe(message -> {
//			System.out.println("Test ==> " + message.toString());
//		});
//		this.chainManager.getChains().get(0).getNode(3).getChannel().subscribe(message -> {
//			System.out.println("Node 3 ==> " + message.toString());
//		});


//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("name", "Johan");
//		jsonObject.put("profession" , "Engineer");
//		JSONObject nested = new JSONObject();
//		nested.put("building", "Building1");
//		jsonObject.put("address", nested);
//		String email = "Hello {{$name}}, We are looking for a  {{$profession}}   living in  {{$address.building}}";
//		System.out.println(EmailBuilder.getEmailText(email, jsonObject));
	}



}
