# mandrill-akka-client
Mandrill Client build on top of Akka HTTP

## TESTING
If you want to run tests:
 * tests are required ```mandrill.test.key``` in config, [how to create mandrill test key](https://mandrill.zendesk.com/hc/en-us/articles/205582447-Does-Mandrill-have-a-test-mode-or-sandbox-)
 * tests are required ```mandrill.test.email``` in config, email witch are set as sender and receiver of email
 * tests generate few templates with label ```integration-test-${suiteName}``` to auto remove it after test, set in config ```mandrill.test.templates.clean = true```