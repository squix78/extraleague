awk -F"[><]" '/<version>/{$3=$3+1;$0="<version>"$3"</version>"}1' appengine-web.xml
