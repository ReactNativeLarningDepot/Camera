import {render} from "@testing-library/react-native";
import SamplePage from "../src/app/sample/SamplePage";

describe('sample', () => {
  it('should ', () => {
    const { getByText } = render(<SamplePage />)

    expect(getByText('sample')).toBeTruthy()
  });
});