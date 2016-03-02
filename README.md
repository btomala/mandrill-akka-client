# mandrill-akka-client
Mandrill Client build on top of Akka HTTP. 

This project is not finished. I planed to use it in my project on university but mandrill remove all free acounts so I decided to abandon it. ([read more](http://blog.mailchimp.com/important-changes-to-mandrill/))

For now you can send email, create webhoosk and manage template. The original plan was to create library wich can coexist as part of akka cluser (as microservice) or simple library in small projects. Feel free to use it.

## TESTING
If you want to run tests:
 * tests are required ```mandrill.test.key``` in config, [how to create mandrill test key](https://mandrill.zendesk.com/hc/en-us/articles/205582447-Does-Mandrill-have-a-test-mode-or-sandbox-)
 * tests are required ```mandrill.test.email``` in config, email witch are set as sender and receiver of email
 * tests generate few templates with label ```integration-test-${suiteName}``` to auto remove it after test, set in config ```mandrill.test.templates.clean = true```
