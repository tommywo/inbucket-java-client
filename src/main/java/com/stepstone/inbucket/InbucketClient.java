/*
Copyright 2016 StepStone Services

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.stepstone.inbucket;

import com.stepstone.inbucket.models.MessageInfo;
import com.stepstone.inbucket.models.Message;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

public class InbucketClient {

    InbucketService service;

    InbucketService simpleService;

    public InbucketClient(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        service = retrofit.create(InbucketService.class);
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .build();
        simpleService = retrofit.create(InbucketService.class);

    }

    /**
     *  Method callss inbuckets GET /api/v1/mailbox/{mailboxName} endpoint
     * @param mailboxName name of mailbox
     * @return List of MailboxEntry
     * @throws IOException if something went wrong
     */
    public List<MessageInfo> getMailbox(String mailboxName) throws IOException {
        return service.getMailbox(mailboxName).execute().body();
    }

    /**
     *  Method calls inbuckets GET /api/v1/mailbox/{mailboxName}/{messageId} endpoint
     * @param mailboxName name of mailbox
     * @param messageId id of message
     * @return List of Message
     * @throws IOException if something went wrong
     */
    public Message getMessage(String mailboxName, String messageId) throws IOException {
        return service.getMessage(mailboxName, messageId).execute().body();
    }

    /**
     * Method calls inbuckets GET /api/v1/mailbox/{mailboxName}/{messageId} endpoint
     * @param messageInfo of message to get
     * @return Message object
     * @throws IOException if something went wrong
     */
    public Message getMessage(MessageInfo messageInfo) throws IOException {
        return service.getMessage(messageInfo.mailbox, messageInfo.id).execute().body();
    }

    /**
     * Method calls inbuckets GET /api/v1/mailbox/{mailboxName}/{messageId}/source endpoint
     * @param mailboxName name of mailbox
     * @param messageId id of message
     * @return message source as string
     * @throws IOException if something went wrong
     */
    public String getMessageSource(String mailboxName, String messageId) throws IOException {
        return simpleService.getMessageSource(mailboxName, messageId).execute().body().string();
    }

    /**
     * Method calls inbuckets GET /api/v1/mailbox/{mailboxName}/{messageId}/source endpoint
     * @param messageInfo of message
     * @return message source
     * @throws IOException if something went wrong
     */
    public String getMessageSource(MessageInfo messageInfo) throws IOException {
        return simpleService.getMessageSource(messageInfo.mailbox, messageInfo.id).execute().body().string();
    }


    /**
     * Method calls inbuckets DELETE /api/v1/mailbox/{mailboxName} endpoint
     * @param mailboxName name of mailbox
     * @return "OK" string on success
     * @throws IOException on error
     */
    public String deleteMailbox(String mailboxName) throws IOException {
             return service.deleteMailbox(mailboxName).execute().body();
    }

    /**
     *  Method calls inbuckets DELETE /api/v1/mailbox/{mailboxName}/{messageId} endpoint
     * @param mailboxName name of mailbox
     * @param messageId id of message
     * @return "OK" string on success null on error
     * @throws IOException on error
     */
    public String deleteMessage(String mailboxName, String messageId) throws IOException {
        return service.deleteMessage(mailboxName, messageId).execute().body();
    }

    /**
     *  Method calls inbuckets DELETE /api/v1/mailbox/{mailboxName}/{messageId} endpoint
     * @param messageInfo message
     * @return "OK" string on success null on error
     * @throws IOException on error
     */
    public String deleteMessage(MessageInfo messageInfo) throws IOException {
        return service.deleteMessage(messageInfo.mailbox, messageInfo.id).execute().body();
    }
}
