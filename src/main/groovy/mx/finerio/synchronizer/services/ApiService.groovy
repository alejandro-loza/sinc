package mx.finerio.synchronizer.services

import groovy.json.JsonSlurper

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import wslite.rest.RESTClient
import wslite.http.auth.HTTPBasicAuthorization

@Service
class ApiService {

  @Value('${api.url}')
  String apiUrl

  @Value('${api.client}')
  String apiClient

  @Value('${api.secret}')
  String secret

  @Value('${api.username}')
  String username

  @Value('${api.password}')
  String password

  Map updateCredential( String id ) throws Exception {

    def path = "/credentials/${id}"
    def body = [:]
    doMethod( path, body, 'put' )

  }

  private Map doMethod( String path, Map body, String method )
      throws Exception {

    try {

      def client = new RESTClient( apiUrl )

      def response = client."${method}"( path: path, headers: getHeaders() ) {
        json body
      }

      def result = response.data ? new String( response.data, 'UTF-8' ) : ''
      return new JsonSlurper().parseText( result ?: '{}' )


    } catch ( Exception e ) {
      handleError( e )
    }

  }

  private Map oauthLogin() throws Exception {

    try {

      def client = new RESTClient( apiUrl )
      client.authorization = new HTTPBasicAuthorization( apiClient, secret )

      def response = client.post( path: '/oauth/token' ) {
        urlenc username: username, password: password, grant_type: 'password'
      }

      def result = response.data ? new String( response.data, 'UTF-8' ) : ''
      return new JsonSlurper().parseText( result ?: '{}' )

    } catch( Exception e ) {
      handleError( e )
    }

  }

  private Map getHeaders() throws Exception {
    [ 'Authorization': "Bearer ${oauthLogin().access_token}" ]
  }

  private void handleError( Exception e ) throws Exception {

    def message = e.response ?
        "${e.response.statusCode} - ${new String(e.response.data ?: '')}" :
        e.message
    throw new IllegalArgumentException( message )

  }

}
