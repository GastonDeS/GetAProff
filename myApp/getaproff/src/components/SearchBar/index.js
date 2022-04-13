import React, { useState } from 'react'
import { Wrapper } from './SearchBar.styles'

// Icon
import SearchIcon from '../../assets/img/search_icon.png'

const SearchBar = () => {
  const [state, setState] = useState('');

  return (
    <Wrapper>
        <img src={SearchIcon} alt='search-icon' />
        <input
          type='text'
          placeholder='Search'
          onChange={event => setState(event.currentTarget.value)}
          value={state}
        />
    </Wrapper>
  )
}

SearchBar.propTypes = {

}

export default SearchBar