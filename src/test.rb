p ENV
#$CLASS_PATH =  'D:/work/workspace/colin-java/bin'
#$LOAD_PATH <<  'D:/work/workspace/colin-java/bin/com/colin/test/ruby'
$LOAD_PATH <<  './src'
#p $LOAD_PATH
require 'java'
#java_require  'RubyTest'
java_import RubyTest
#include_class  'com.colin.test.ruby.RubyTest'
class JavaTest
  x = RubyTest.new
  x.check  
end