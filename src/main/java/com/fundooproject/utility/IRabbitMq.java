package com.fundooproject.utility;

import com.fundooproject.dto.RabbitMqDto;

public interface IRabbitMq {

	void sendMessageToQueue(RabbitMqDto rabbitMqDto); // producer

	void receiveMessage(RabbitMqDto rabbitMqDto); // listener

	void send(RabbitMqDto rabbitMqDto);
}