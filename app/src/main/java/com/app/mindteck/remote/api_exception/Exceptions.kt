package com.app.mindteck.remote.api_exception

import java.io.IOException

class ApiException(message:String) : IOException(message)
class InternetException(message:String) : IOException(message)