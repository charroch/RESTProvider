VERSION_NUMBER = "0.0.1"

raise "ANDROID_SDK or ANDROID_HOME variable needs to point to your android installation" unless ENV['ANDROID_SDK'] || ENV['ANDROID_HOME']
sdk = ENV['ANDROID_SDK'] || ENV['ANDROID_HOME']

GROUP = "RESTProvider"
COPYRIGHT = "Apache licence 2.0"

JACKSON = ['org.codehaus.jackson:jackson-mapper-asl:jar:1.5.1', 'org.codehaus.jackson:jackson-core-asl:jar:1.5.1']
SIGNPOST = ['oauth.signpost:signpost-commonshttp4:jar:1.2', 'oauth.signpost:signpost-core:jar:1.2']
APACHE_HTTP_CLIENT = ['org.apache.httpcomponents:httpclient:jar:4.1-alpha2', 'org.apache.httpcomponents:httpcore:jar:4.1-beta1']
TEST = ['org.mockito:mockito-all:jar:1.8.4', 'junit:junit:jar:4.8.1', 'com.jayway.awaitility:awaitility:jar:1.0', 'javassist:javassist:jar:3.8.0.GA', 'org.jbehave:jbehave-core:jar:2.5.9']
ANDROID_TEST = ['com.google.android:android:jar:2.2.1']
MIXML = ['com.github.charroch:MiXML:jar:0.0.5-SNAPSHOT']
XPP = ['xerces:xmlParserAPIs:jar:2.6.2','xpp3:xpp3:jar:1.1.4c']

android_layout = Layout.new
android_layout[:source, :main, :java] = 'src'
android_layout[:target, :main, :classes] = 'bin'
android_layout[:target, :main] = 'bin'
android_layout[:target] = 'bin'

# Specify Maven 2.0 remote repositories here, like this:
repositories.remote << "http://www.ibiblio.org/maven2/"
repositories.remote << "https://repository.sonatype.org"

desc "The Restprovider project"
define "buildr" do

  project.version = VERSION_NUMBER
  project.group = GROUP
  manifest["Implementation-Vendor"] = COPYRIGHT
  compile.options.target = '1.6'

  desc 'RESTProvider as Android library'
  define "RESTProvider", :layout=>android_layout do
    compile.with JACKSON, SIGNPOST, MIXML,  APACHE_HTTP_CLIENT, ANDROID_TEST
  end

  define "RESTProviderLocalTest" do
    puts project('buildr:RESTProvider')
    compile.with project(:RESTProvider).compile.dependencies
    test.with project(:RESTProvider).compile.dependencies
  end

  define "RESTProviderTest" do
  end

end
