import re

# Using re package to replace '@...' and '#...' in text content of Instagram post with appropriate HTML links.

def replaceToLink(matchObj):
    atPattern = '<a href=\"https://www.instagram.com/%s/\">%s</a>'
    sharpPattern = '<a href=\"https://www.instagram.com/explore/tags/%s/\">%s</a>'

    # About using group() to access matching result:
    # group(0): matched string itself. group(n): result of nth regex group matching.
    #   ex) if regex was '((1st)|(2nd))' and str was '2nd',
    #       group(0): 2nd, group(1): None, group(2): 2nd.
    matchedStr = matchObj.group(0)
    if matchedStr[0] == '@':
        return atPattern % (matchedStr[1:], matchedStr)
    elif matchedStr[0] == '#':
        return sharpPattern % (matchedStr[1:], matchedStr)
    else:
        return matchedStr + "??"    # Error case.

if __name__ == '__main__':
    regex = '(@[^\s]+|#[^\s]+)'
    caption = "just plain texts #instagram @ehwaz no other meaning #한글테스트 test test@eee etest#tetet"
    print(re.sub(regex, replaceToLink, caption))