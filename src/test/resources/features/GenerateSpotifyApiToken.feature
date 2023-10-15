#
@spotify
Feature: Generate  the refresh token when the Spotify authToken expires


  @spotify
  Scenario Outline: Refresh token when authToken expires
   Given spotify auth token is generated
   When aut grant code is generated
	 Then generate the refresh token 